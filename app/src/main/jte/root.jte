@import hexlet.code.dto.RootPage
@param RootPage page

@template.layout.page(

content = @`
    <div class="mb-3">
        <h1>${page.getMessage()}</h1>
    </div>
`
)
