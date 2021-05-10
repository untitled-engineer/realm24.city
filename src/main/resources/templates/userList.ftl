<#import "parts/common.ftl" as c>

<@c.page>
    <h5>List of Users</h5>
    <table>
        <thead>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.getId()}">edit</a></td>
            </tr>
        <#else>
            No any
        </#list>
        </tbody>
    </table>
</@c.page>
