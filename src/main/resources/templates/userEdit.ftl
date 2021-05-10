<#import "parts/common.ftl" as c>

<@c.page>
    <h5>Edit User</h5>
    <form action="/user" method="post">
        <label>
            <input type="text" name="username" value="${user.username}">
        </label>
        <#list roles as role>
            <div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                </label>
                ${role}
            </div>
        </#list>
        <input type="hidden" value="${user.getId()}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@c.page>