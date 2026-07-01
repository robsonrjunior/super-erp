## 1. I18n translations

- [x] 1.1 Add `footer.dark.mode` and `footer.light.mode` keys to `src/main/webapp/i18n/pt-br/global.json`
- [x] 1.2 Add `footer.dark.mode` and `footer.light.mode` keys to `src/main/webapp/i18n/en/global.json`
- [x] 1.3 Add `footer.dark.mode` and `footer.light.mode` keys to `src/main/webapp/i18n/es/global.json`

## 2. PrimeNG dark mode configuration

- [x] 2.1 Add `darkModeSelector: '.dark-mode'` option to `providePrimeNG` in `src/main/webapp/app/app.config.ts`

## 3. Footer component updates

- [x] 3.1 Update `footer.ts`: inject `DOCUMENT`, use `Renderer2` to toggle `.dark-mode` class and `data-bs-theme` attribute on `<html>`, read/write `localStorage`, add `isDarkMode` signal and `toggleDarkMode()` method
- [x] 3.2 Update `footer.html`: add theme toggle button before the language selector with sun/moon icons (`pi pi-sun` / `pi pi-moon`) and translated label
- [x] 3.3 Update `footer.scss`: style the toggle button to match the footer's dark visual

## 4. Global styles

- [x] 4.1 Update `src/main/webapp/content/scss/global.scss`: make body background, link color, and footer border respond to `.dark-mode` class using CSS overrides
- [x] 4.2 Update `src/main/webapp/app/layouts/main/main.scss`: remove hardcoded white backgrounds, use theme-responsive values

## 5. Navbar theme responsiveness

- [x] 5.1 Update `navbar.html` and `navbar.scss` in `src/main/webapp/app/layouts/navbar/`: replace hardcoded `navbar-dark bg-dark` with classes that respond to the active theme mode

## 6. Validation

- [x] 6.1 Run `./npmw run lint` and ensure no ESLint errors
- [x] 6.2 Run `./npmw test` and ensure all Vitest tests pass
- [ ] 6.3 Manually verify the toggle works: click switches icon/text, mode persists on reload, PrimeNG and Bootstrap components render correctly in both modes
