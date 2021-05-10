<h3>Message editor</h3>
<form method="post" enctype="multipart/form-data">
    <label>
        <input type="text"
               name="text"
               class="form-control <#if (errorMap['textError'])??>is-invalid</#if>"
               value="<#if message??>${message.text}</#if>"
               placeholder="Введите сообщение"/>
        <#if (errorMap['textError'])??>
            <div class="invalid-feedback">
                ${errorMap['textError']}
            </div>
        </#if>
    </label>
    <label>
        <input type="text" name="tag" placeholder="Ключевые слова"/>
    </label>
    <input type="file" name="file">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="hidden" name="id" value="<#if message??>${message.id}</#if>">
    <button type="submit">Сохранить</button>
</form>