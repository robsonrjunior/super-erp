# Primeng App Shell

## Purpose

The PrimeNG app shell is the visual container for the entire application. It wraps the navbar, page content, and footer inside a PrimeNG `p-card` component, providing a cohesive surface background color from the Aura theme.

## Requirements

### Requirement: Application shell uses PrimeNG card
The application layout SHALL wrap the entire screen (navbar, routed content, footer) inside a PrimeNG `p-card` component, using the PrimeNG Aura theme surface background color as the application background.

#### Scenario: Full application inside a PrimeNG card
- **WHEN** the application loads
- **THEN** a PrimeNG `p-card` component wraps the navbar, page content area, and footer
- **AND** the card fills the full viewport height

#### Scenario: Card background is the app background
- **WHEN** the application is displayed
- **THEN** the card surface background color (from the PrimeNG Aura theme) serves as the visible background for the entire application

### Requirement: Sticky footer behavior preserved
The layout SHALL keep the footer at the bottom of the viewport when page content is shorter than the viewport height.

#### Scenario: Footer at bottom with short content
- **WHEN** navigating to a page with minimal content
- **THEN** the footer is positioned at the bottom of the viewport
- **AND** the content area fills the remaining space between the navbar and footer

#### Scenario: Footer pushed down with long content
- **WHEN** navigating to a page with content taller than the viewport
- **THEN** the footer appears after the content ends (scrollable)

### Requirement: Dev ribbon remains visible
The existing page ribbon component (`jhi-page-ribbon`) SHALL remain functional and visually unchanged.

#### Scenario: Dev ribbon visible in development
- **WHEN** running in development mode
- **THEN** the page ribbon is displayed in its original position above the card layout
