@import hexlet.code.dto.urls.UrlsPage
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
        @for(var url : page.getUrlList())
            <tr>
                <td>
                    ${url.getId()}
                </td>
                <td>
                    <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                </td>
                <td>
                    ${String.valueOf(url.getCreatedAt())}
                </td>
            </tr>
        @endfor
    </table>
`
)
