@import hexlet.code.dto.urls.UrlPage
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
`
)
