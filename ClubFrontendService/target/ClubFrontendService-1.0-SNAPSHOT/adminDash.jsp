<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body onload="fetchApplications()">

    <div class="container">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <h2>Admin Control Panel</h2>
            <button class="btn-primary" style="background-color: #dc3545;" onclick="location.href='index.jsp'">Logout</button>
        </div>
        <p>Review student statements and manage pending club applications by organization.</p>

        <div id="adminTableContainer">
            <p style="text-align: center; color: #666;">Aggregating data from microservices...</p>
        </div>
    </div>

    <script>
        async function fetchApplications() {
            const token = "<%= session.getAttribute("token") %>";
            const b1_url = 'http://localhost:8080/ClubRegistrationService/webresources/registration/allApplications';
            const b2_url = 'http://localhost:8080/ClubManagementService/webresources/clubs';

            try {
                // Fetch from both services simultaneously
                const [resB1, resB2] = await Promise.all([
                    fetch(b1_url, { headers: { 'Authorization': 'Bearer ' + token } }),
                    fetch(b2_url)
                ]);

                if (!resB1.ok) throw new Error("Unauthorized or B1 offline");
                
                const xmlApps = await resB1.text();
                const xmlClubs = await resB2.text();
                let parser = new DOMParser();

                // Create a Map of Club IDs to Club Names from B2
                let clubDoc = parser.parseFromString(xmlClubs, "text/xml");
                let clubNodes = clubDoc.getElementsByTagName("club");
                let clubMap = {};
                
                for (let i = 0; i < clubNodes.length; i++) {
                    let id = clubNodes[i].getElementsByTagName("club_id")[0]?.textContent || 
                             clubNodes[i].getElementsByTagName("id")[0]?.textContent;
                    let name = clubNodes[i].getElementsByTagName("club_name")[0]?.textContent || 
                               clubNodes[i].getElementsByTagName("name")[0]?.textContent;
                    clubMap[id] = name;
                }

                // Process Applications from B1
                let appDoc = parser.parseFromString(xmlApps, "text/xml");
                let apps = appDoc.getElementsByTagName("application");

                if (apps.length === 0) {
                    document.getElementById("adminTableContainer").innerHTML = "<p style='text-align:center;'>No pending applications found.</p>";
                    return;
                }

                // Group Applications by Club Name
                let grouped = {};
                for (let i = 0; i < apps.length; i++) {
                    let id = apps[i].getElementsByTagName("clubId")[0]?.textContent;
                    let name = clubMap[id] || ("Club ID: " + id);
                    if (!grouped[name]) grouped[name] = [];
                    grouped[name].push(apps[i]);
                }

                // Build HTML Tables by Club
                let html = "";
                for (let clubName in grouped) {
                    html += "<div class='club-group' style='margin-bottom: 50px;'>";
                    html += "<h3 style='border-left: 5px solid #2c3e50; padding-left: 12px; color: #2c3e50; text-transform: uppercase; font-size: 16px; margin-bottom:15px;'>Club: " + clubName + "</h3>";
                    html += "<table><tr><th>Student</th><th>Statement</th><th>Actions</th></tr>";
                    
                    grouped[clubName].forEach(app => {
                        let user = app.getElementsByTagName("user")[0]?.textContent;
                        let stmt = app.getElementsByTagName("statement")[0]?.textContent;
                        let appId = app.getElementsByTagName("id")[0]?.textContent;
                        
                        html += "<tr>";
                        html += "<td><strong>" + user + "</strong></td>";
                        html += "<td style='max-width:400px; font-style:italic;'>\"" + stmt + "\"</td>";
                        html += "<td>";
                        html += " <button class='btn-primary' onclick='processApp(" + appId + ", \"Approved\")'>Approve</button>";
                        html += " <button class='btn-primary' style='background-color:#6c757d; margin-left:5px;' onclick='processApp(" + appId + ", \"Declined\")'>Decline</button>";
                        html += "</td></tr>";
                    });
                    html += "</table></div>";
                }
                document.getElementById("adminTableContainer").innerHTML = html;

            } catch (err) {
                document.getElementById("adminTableContainer").innerHTML = 
                    "<p style='color:red; text-align:center;'><strong>Access Denied:</strong> " + err.message + ". Ensure both services are running and you are logged in as admin.</p>";
            }
        }

        function processApp(appId, status) {
            const token = "<%= session.getAttribute("token") %>";
            const b1_status_url = 'http://localhost:8080/ClubRegistrationService/webresources/registration/updateStatus';

            const params = new URLSearchParams();
            params.append('appId', appId);
            params.append('status', status);

            fetch(b1_status_url, {
                method: 'POST',
                headers: { 
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: params
            })
            .then(response => {
                if(response.ok) {
                    alert("Application " + status + " successfully.");
                    fetchApplications();
                } else {
                    alert("Update failed. Error: " + response.status);
                }
            })
            .catch(err => alert("Communication error with B1 Registration Service."));
        }
    </script>
</body>
</html>