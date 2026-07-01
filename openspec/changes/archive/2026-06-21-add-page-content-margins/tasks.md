## 1. Main content area margins

- [x] 1.1 Add `--app-content-max-width` CSS custom property to `global.scss` (e.g., 1200px)
- [x] 1.2 Add `max-width: var(--app-content-max-width)` and `margin: 0 auto` to `.app-content` in `main.scss`
- [x] 1.3 Add horizontal padding to `.app-content` for breathing room on narrow screens (e.g., `padding: 0 1rem`)

## 2. Navbar inner container

- [x] 2.1 Add a `<div class="navbar-content">` wrapper inside the navbar template (`navbar.html`) around the existing `.navbar-container`
- [x] 2.2 Add `max-width: var(--app-content-max-width)` and `margin: 0 auto` to `.navbar-content` in `navbar.scss`

## 3. Footer inner container

- [x] 3.1 Add a `<div class="footer-content">` wrapper inside the footer template (`footer.html`) around the existing footer content
- [x] 3.2 Move horizontal padding (`px-4`) from the footer element to `.footer-content` and add `max-width: var(--app-content-max-width)` plus `margin: 0 auto` via `footer.scss`

## 4. Validation

- [x] 4.1 Run `./npmw run lint` to verify no ESLint errors
- [x] 4.2 Run `./npmw run start` and visually verify margins appear on home page, entity list, entity detail
- [x] 4.3 Verify navbar background still spans full width with content centered
- [x] 4.4 Verify footer background still spans full width with content centered
- [x] 4.5 Verify login page is unaffected (no extra margins, navbar hidden)
