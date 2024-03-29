@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.page(
page = page,
content = @`
    <div class="mx-auto p-4 py-md-5">
        <form action="${NamedRoutes.urls()}" method="post">
            <div class="mb-3">
                <label class="form-label">
                    Название
                    <input type="text" class="form-control" name="url" required="required" />
                </label>
            </div>
            <input type="submit" class="btn btn-primary" value="Проверить" />
        </form>
    </div>
`
)
