<%@ page import="model.ElectionResult" %>
<% 
    ElectionResult[]  results = (ElectionResult[]) request.getAttribute("results"); 
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/bootstrap/css/bootstrap.min.css">
    <title>Resultat</title>
</head>
<body>
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">Resultat par bureau de vote</h4>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th> Code bureau de vote </th>
                            <th> Bureau de vote </th>
                            <th> Candidat </th>
                            <th> Voix </th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for(int i=0; i < results.length; i++) { %>
                            <tr>
                                <td class="py-1">
                                    <%= results[i].getCodeBv() %>
                                </td>
                                <td>
                                    <%= results[i].getBureauDeVote() %>
                                </td>
                                <td>
                                    <%= results[i].getCandidatId() + " " + results[i].getName() + " " + results[i].getPartiPolitique() %>
                                </td>
                                <td>
                                    <%= results[i].getVoix() %>
                                </td>
                            </tr>
                            <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>