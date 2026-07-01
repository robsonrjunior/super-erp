## 1. PrimeNG Card Setup

- [x] 1.1 Import `CardModule` from `primeng/card` in `src/main/webapp/app/layouts/main/main.ts`

## 2. Layout Template

- [x] 2.1 Replace the layout in `src/main/webapp/app/layouts/main/main.html`: remove `.app-layout` wrapper, `.app-content` container, and `.card.jh-card` div; wrap navbar outlet, content outlet, and footer inside a single `p-card`
- [x] 2.2 Ensure the card body uses flexbox column layout so the content area flex-grows and pushes the footer to the bottom (sticky footer behavior)

## 3. Styles

- [x] 3.1 Update `src/main/webapp/app/layouts/main/main.scss`: apply `min-height: 100vh` and `display: flex; flex-direction: column` on `:host`; remove old `.app-layout` and `.app-content` rules
- [x] 3.2 Remove unused `.jh-card` style rule from `src/main/webapp/content/scss/global.scss` (no longer referenced after layout change)

## 4. Verification

- [x] 4.1 Run `./npmw run start` to build and verify the app compiles without errors
- [x] 4.2 Verify the PrimeNG card wraps the entire application with correct background color
- [x] 4.3 Verify sticky footer: footer stays at viewport bottom on short pages, scrolls normally on long pages
- [x] 4.4 Verify the dev page ribbon still displays correctly in development mode
