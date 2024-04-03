@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.page(
page = page,
content = @`
    <main role="main" class="inner cover">
        <h1 class="cover-heading">Анализатор страниц</h1>
        <p class="lead">бесплатно проверяйте сайт на SEO-пригодность</p>
            <form class="row g-3" action="${NamedRoutes.urls()}" method="post">
                <div class="col-auto">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon3">https://www.example.com</span>
                        </div>
                        <label for="url"></label><input type="text" class="form-control" name="url"
                                                        id="url" aria-describedby="basic-addon3">
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3">Проверить</button>
                </div>
            </form>
    </main>
`
)
