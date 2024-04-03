@import hexlet.code.dto.urls.UrlChecksPage
@import hexlet.code.util.FormatTimestamp
@import hexlet.code.util.NamedRoutes
@param UrlChecksPage page

@template.layout.page(
page = page,
content = @`
    <main role="main" class="inner cover">
        <div style="text-align: left">
        <h3>Сайт: ${page.getUrl().getName()}</h3>
        <hr>
            <p></p>
        <table class="table table-dark table-borderless">
            <tr>
                <td>
                    ID
                </td>
                <td>
                    ${page.getUrl().getId()}
                </td>
            </tr>
            <tr>
                <td>
                    Имя
                </td>
                <td>
                    ${page.getUrl().getName()}
                </td>
            </tr>
            <tr>
                <td>
                    Дата создания
                </td>
                <td>
                    ${FormatTimestamp.convert(page.getUrl().getCreatedAt())}
                </td>
            </tr>
        </table>


            <h3 style="text-align: left">Проверки</h3>
            <form action="${NamedRoutes.urlCheck(page.getUrl().getId())}" method="post">
                <input type="submit" class="btn btn-primary" value="Запустить проверку" />
            </form>
        </div>
        <table class="table table-dark table-hover">
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
    </main>
`
)
