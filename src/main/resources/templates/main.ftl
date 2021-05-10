<#import "parts/common.ftl" as c>

<@c.page>
    <#include "parts/message-edit.ftl" />
    <h1>Сообщения</h1>
    <hr>
    <div>
        <h6>Filter</h6>
        <form action="/filter" method="post">
            <label>
                <input type="text" name="filter" value="${filter!}"/>
            </label>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit">Найти</button>
        </form>
    </div>
    <hr>

    <#include "parts/messages-list.ftl" />
</@c.page>