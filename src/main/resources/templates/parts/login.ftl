<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-3 row">
            <label for="username" class="col-sm-2 col-form-label">
                <span>User Name :</span>
                <div class="col-sm-4">
                    <input class="form-control <#if (usernameError)??>is-invalid</#if>"
                           type="text"
                           value="<#if user??>${user.username}</#if>"
                           name="username"/>
                    <#if (usernameError)??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
            </label>
        </div>
        <div class="mb-3 row">
            <label for="password" class="col-sm-2 col-form-label">
                <span>Password:</span>
                <div class="col-sm4">
                    <input class="form-control <#if (passwordError)??>is-invalid</#if>"
                           type="password"
                           name="password"/>
                    <#if (passwordError)??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </label>
        </div>
        <#if isRegisterForm>
            <div class="mb-3 row">
                <label for="password2" class="col-sm-2 col-form-label">
                    <span>Retype password:</span>
                    <div class="col-sm4">
                        <input class="form-control <#if (password2Error)??>is-invalid</#if>"
                               type="password"
                               name="password2"/>
                        <#if (password2Error)??>
                            <div class="invalid-feedback">
                                ${password2Error}
                            </div>
                        </#if>
                    </div>
                </label>
            </div>
            <div class="mb-3 row">
                <label for="email" class="col-sm-2 col-form-label">
                    <span>Email:</span>
                    <div class="col-sm4">
                        <input class="form-control <#if (emailError)??>is-invalid</#if>"
                               type="email"
                               value="<#if user??>${user.email}</#if>"
                               name="email"/>
                        <#if (emailError)??>
                            <div class="invalid-feedback">
                                ${emailError}
                            </div>
                        </#if>
                    </div>
                </label>
            </div>
        <#else >
            <a href="/registration">Регистрация</a>
        </#if>
        <button class="btn btn-primary" type="submit">
            <#if isRegisterForm>
                <span>Зарегистрироваться</span>
            <#else>
                <span>Авторизировать</span>
            </#if>
        </button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">
            <span>Выйти</span>
        </button>
    </form>
</#macro>