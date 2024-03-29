@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.FormatTimestamp
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <h1>Сайты</h1>
    <div class="mb-3">
        <a href="${NamedRoutes.home()}">назад</a>
    </div>
    <table class="table table-striped">
        <thead>
        <th>ID</th>
        <th>Имя</th>
        <th>Последняя проверка</th>
        <th>Код ответа</th>
        </thead>
        @if (page.getUrlList().isEmpty())
            <tr>
                <td class="text-center" colspan="5"><i>сайты ещё не добавлены</i></td>
            </tr>
        @else
            @for(var url : page.getUrlList())
                <tr>
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                    </td>
                    <td>
                        @if (url.getLastCheck() != null)
                            ${FormatTimestamp.convert(url.getLastCheck().getCreatedAt())}
                        @else
                            —
                        @endif
                    </td>
                    <td>
                        @if (url.getLastCheck() != null)
                            ${url.getLastCheck().getStatusCode()}
                        @else
                            —
                        @endif
                    </td>
                </tr>
            @endfor
        @endif
    </table>
`
)
