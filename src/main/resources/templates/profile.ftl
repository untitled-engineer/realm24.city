<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${username} profile page</h5>
    ${message!}
    <form method="post">
        <div class="mb-3 row">
            <label for="username" class="col-sm-2 col-form-label">
                <span>User Name :</span>
                <div class="col-sm-4"><input class="form-control" type="text" name="username"/></div>
            </label>
        </div>
        <div class="mb-3 row">
            <label for="password" class="col-sm-2 col-form-label">
                <span>Password:</span>
                <div class="col-sm4">
                    <input class="form-control" type="password" name="password"/>
                </div>
            </label>
        </div>
        <div class="mb-3 row">
            <label for="password" class="col-sm-2 col-form-label">
                <span>Email:</span>
                <div class="col-sm4">
                    <input class="form-control"
                           type="email"
                           name="email"
                           value="${email!''}"
                           placeholder="your-mail@domain.com"/>
                </div>
            </label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">
            <span>Сохранить</span>
        </button>
    </form>


</@c.page>
