<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Switter</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a href="/" class="nav-link">Home</a>
                </li>
                <#if user??>
                    <li class="nav-item">
                        <a href="/main" class="nav-link">Messages</a>
                    </li>
                    <li class="nav-item">
                        <a href="/user/${currentUserId}/messages" class="nav-link">My Messages</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a href="/user" class="nav-link">User list</a>
                    </li>
                </#if>
                <#if user??>
                    <li class="nav-item">
                        <a href="/user/profile" class="nav-link">Profile</a>
                    </li>
                </#if>
            </ul>

            <div class="navbar-text mr-3">${name}</div>
            <@l.logout />
        </div>
    </div>
</nav>