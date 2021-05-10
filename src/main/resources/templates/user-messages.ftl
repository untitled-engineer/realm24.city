<#import "parts/common.ftl" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "parts/message-edit.ftl" />
    </#if>
    <h1>Сообщения</h1>
    <hr>
    <#include "parts/messages-list.ftl" />
</@c.page>
