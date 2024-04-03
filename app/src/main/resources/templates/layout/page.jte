@import gg.jte.Content
@import hexlet.code.dto.BasePage
@import hexlet.code.dto.urls.UrlChecksPage
@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Анализатор страниц</title>
    <link rel="apple-touch-icon" sizes="180x180" href="/favicons/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicons/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicons/favicon-16x16.png">
    <link rel="manifest" href="/favicons/site.webmanifest">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
    <link href="/style.css" rel="stylesheet">
</head>
<body  class="text-center">
    <div class="cover-container d-flex mx-auto flex-column">
        <header class="masthead mb-auto">
            <div class="inner">
                <a href="/"><h3 class="masthead-brand">Анализатор страниц</h3></a>
                <nav class="nav nav-masthead justify-content-right">
                    @if(page instanceof UrlsPage)
                        <a class="nav-link" href="${NamedRoutes.home()}">Главная</a>
                        <a class="nav-link active" href="${NamedRoutes.urls()}">Сайты</a>
                    @elseif(page instanceof UrlChecksPage)
                        <a class="nav-link" href="${NamedRoutes.home()}">Главная</a>
                        <a class="nav-link active" href="${NamedRoutes.urls()}">Сайты</a>
                    @else
                        <a class="nav-link active" href="${NamedRoutes.home()}">Главная</a>
                        <a class="nav-link" href="${NamedRoutes.urls()}">Сайты</a>
                    @endif
                </nav>
            </div>
        </header>

        @if(page != null && page.getFlash() != null)
            <div class="alert alert-${page.getFlashType()} alert-dismissible fade show" role="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        <div class="inner">
            ${content}
        </div>

        <footer class="mastfoot mt-auto">
            <div class="inner">
                <p>Created by <a href="https://ru.hexlet.io/">Hexlet</a></p>
            </div>
        </footer>
    </div>
</body>
</html>
