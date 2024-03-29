@import hexlet.code.dto.urls.UrlChecksPage
@import hexlet.code.util.FormatTimestamp
@import hexlet.code.util.NamedRoutes
@param UrlChecksPage page

@template.layout.page(
page = page,
content = @`
    <div class="mb-3">
        <h1>${page.getUrl().getId()}</h1>
    </div>
    <div class="mb-3">
        ${page.getUrl().getName()}
    </div>
    <div class="mb-3">
        ${FormatTimestamp.convert(page.getUrl().getCreatedAt())}
    </div>
    <div><h3>Проверки</h3></div>
    <form action="${NamedRoutes.urlCheck(page.getUrl().getId())}" method="post">
        <input type="submit" class="btn btn-primary" value="Запустить проверку" />
    </form>
    <table class="table table-striped">
        <thead>
            <th>ID</th>
            <th>Код ответа</th>
            <th>title</th>
            <th>h1</th>
            <th>description</th>
            <th>Дата проверки</th>
        </thead>
        @if (page.getUrlChecks().isEmpty())
            <tr>
                <td class="text-center" colspan="6"><i>проверки ещё не запускались</i></td>
            </tr>
        @else
            @for(var check : page.getUrlChecks())
                <tr>
                    <td>
                        ${check.getId()}
                    </td>
                    <td>
                        ${check.getStatusCode()}
                    </td>
                    <td>
                        ${check.getTitle()}
                    </td>
                    <td>
                        ${check.getH1()}
                    </td>
                    <td>
                        ${check.getDescription()}
                    </td>
                    <td>
                        ${FormatTimestamp.convert(check.getCreatedAt())}
                    </td>
                </tr>
            @endfor
        @endif
    </table>
`
)
