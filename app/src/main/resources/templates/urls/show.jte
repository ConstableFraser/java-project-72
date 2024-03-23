@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

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
        ${String.valueOf(page.getUrl().getCreatedAt())}
    </div>
    <div><h3>Проверки</h3></div>
    <form action="${NamedRoutes.urlCheck(page.getUrl().getId())}" method="post">
        <input type="submit" class="btn btn-primary" value="Запустить проверку" />
    </form>
    <table class="table table-striped">
        <thead>
            <th>ONE</th>
            <th>TWO</th>
            <th>THREE</th>
        </thead>
            <tr>
                <td>
                    N1
                </td>
                <td>
                    N2
                </td>
                <td>
                    N3
                </td>
            </tr>
    </table>    
`
)
