<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Switter</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Template Polymer 3.0 App">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta name="theme-color" content="#fff">

        <!--
        <link rel="icon" href="manifest/favicon.ico">
        <link rel="manifest" href="/static/manifest.json">
        -->

        <script src="/static/js/node_modules/@webcomponents/webcomponentsjs/webcomponents-loader.js" async></script>

        <script type="module" >
        </script>

        <link rel="stylesheet" href="/static/main.css">
    </head>

    <body>

        <#include "navbar.ftl">

        <div class="container mt-5">
            <#nested>
        </div>

        <script type="module" src="/static/js/out/realm-app.js" async></script>
    </body>

    </html>
</#macro>