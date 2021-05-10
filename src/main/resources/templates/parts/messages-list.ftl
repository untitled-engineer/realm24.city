<#include "security.ftl">

<div class="card-columns">
    <#list messages as message>
        <div class="card my-6">
            <#if message.filename??>
                <div>
                    <img src="/img/${message.filename}" class="card-img-top">
                </div>
            </#if>
            <div class="sm-6">
                <span>${message.text}</span>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user/${message.author.id}/messages">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a href="/user/${message.author.id}/messages?message=${message.id}"
                       class="btn btn-primary"
                    >Edit</a>
                </#if>
            </div>
        </div>
    <#else>
        <p>No Messages.</p>
    </#list>
</div>
