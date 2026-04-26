<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Apply for Club</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

    <div class="form-card">
        <h2>Submit Application</h2>
        
        <p style="font-size: 18px;">You are applying for: <strong id="displayClubName" style="color:#2c3e50;"></strong></p>
        
        <div style="margin-top: 30px;">
            <label for="statement"><strong>Statement of Interest:</strong></label>
            <textarea id="statement" rows="8" placeholder="Please describe your background and why you wish to join this club..."></textarea>
        </div>
        
        <button class="btn-primary btn-full" onclick="submitApplication()">Confirm & Submit</button>
        <button class="btn-link" onclick="location.href='main.jsp'">Cancel and Return to Dashboard</button>
    </div>

    <script>
        const urlParams = new URLSearchParams(window.location.search);
        const clubId = urlParams.get('id');
        const clubName = urlParams.get('name');
        document.getElementById('displayClubName').innerText = clubName;

        function submitApplication() {
            const token = "<%= session.getAttribute("token") %>";
            const user = "<%= session.getAttribute("user") %>";
            const statement = document.getElementById("statement").value;

            if(!statement.trim()) {
                alert("The statement of interest cannot be empty.");
                return;
            }

            const b1_url = 'http://localhost:8080/ClubRegistrationService/webresources/registration/apply';
            const params = new URLSearchParams();
            params.append('user', user);
            params.append('clubId', clubId);
            params.append('statement', statement);

            fetch(b1_url, {
                method: 'POST',
                headers: { 
                    'Authorization': 'Bearer ' + token 
                },
                body: params
            })
            .then(response => {
                if(response.ok) {
                    alert("Application successful! Redirecting home...");
                    window.location.href = "main.jsp";
                } else {
                    alert("Application failed. Backend server returned error: " + response.status);
                }
            })
            .catch(err => alert("Connection error: Cannot reach the Registration Service."));
        }
    </script>
</body>
</html>