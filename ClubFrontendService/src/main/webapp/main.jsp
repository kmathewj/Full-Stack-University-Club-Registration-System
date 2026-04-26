<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Club Discovery</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body onload="fetchClubs()">

    <div class="container">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <h2>Welcome, <%= session.getAttribute("user") %>!</h2>
            <button class="btn-primary" style="background-color: #dc3545;" onclick="location.href='index.jsp'">Logout</button>
        </div>
        <p>Browse the list of available clubs and click 'Apply' to submit your interest.</p>

        <div id="clubTableContainer">
            <p style="text-align: center; color: #666;">Contacting Club Management Service...</p>
        </div>
    </div>

    <script>
        function fetchClubs() {
            // URL Backend 2
            const b2_url = 'http://localhost:8080/ClubManagementService/webresources/clubs';
            
            fetch(b2_url)
                .then(res => {
                    if(!res.ok) throw new Error("B2 not reachable");
                    return res.text();
                })
                .then(xmlString => {
                    let parser = new DOMParser();
                    let xmlDoc = parser.parseFromString(xmlString, "text/xml");
                    let clubs = xmlDoc.getElementsByTagName("club");
                    
                    let table = "<table><tr><th>ID</th><th>Club Name</th><th>Action</th></tr>";
                    for (let i = 0; i < clubs.length; i++) {
                        let id = clubs[i].getElementsByTagName("club_id")[0]?.textContent || clubs[i].getElementsByTagName("id")[0]?.textContent;
                        let name = clubs[i].getElementsByTagName("club_name")[0]?.textContent || clubs[i].getElementsByTagName("name")[0]?.textContent;
                        
                        table += "<tr><td>" + id + "</td><td><strong>" + name + "</strong></td>";
                        table += "<td><button class='btn-primary' onclick='goToApply(\"" + id + "\", \"" + name + "\")'>Apply Now</button></td></tr>";
                    }
                    table += "</table>";
                    document.getElementById("clubTableContainer").innerHTML = table;
                })
                .catch(err => {
                    document.getElementById("clubTableContainer").innerHTML = 
                        "<p style='color:red;'>Unable to load clubs. Ensure ClubManagementService is running.</p>";
                });
        }

        function goToApply(id, name) {
            window.location.href = "apply.jsp?id=" + encodeURIComponent(id) + "&name=" + encodeURIComponent(name);
        }
    </script>
</body>
</html>