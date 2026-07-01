## Context

The application currently has no auth guard on the root path (`''`). When an unauthenticated user opens the app, the Angular router resolves `path: ''` to the Home component, which shows a "You are not logged in" message with links to log in or register. The `UserRouteAccessService` guard (`src/main/webapp/app/core/auth/user-route-access.service.ts`) already exists and is used by other routes (e.g., admin) to redirect unauthenticated users to `/login`. The login component already handles the case where an already-authenticated user navigates directly to `/login` (it redirects to home).

## Goals / Non-Goals

**Goals:**
- Redirect unauthenticated users from the root path (`/`) directly to `/login`
- Authenticated users continue to see the home page when navigating to `/`

**Non-Goals:**
- No changes to the login page, register page, or any backend endpoints
- No changes to how other guarded routes work
- No changes to the post-login redirect flow (stored URL → redirect back)
- No visual changes to the home component itself

## Decisions

### Add `canActivate: [UserRouteAccessService]` to the home route

**Choice**: Reuse the existing `UserRouteAccessService` guard on the `path: ''` home route in `app.routes.ts`.

**Rationale**: The existing guard already implements the exact behavior needed:
1. Calls `accountService.identity()` to check auth state
2. If not authenticated: stores the current URL and redirects to `/login`
3. If authenticated: allows access (no authorities check when `authorities` is undefined)

After login, the post-login flow in `AccountService.identity()` calls `navigateToStoredUrl()`, which redirects back to `/` — the home page for the now-authenticated user. This is the desired behavior.

**Alternatives considered**:
- **Modify the `Main` component's `ngOnInit`** to redirect after identity check: This would couple auth routing logic into the layout component rather than keeping it at the routing layer where it belongs.
- **Create a new custom guard**: Unnecessary duplication — the existing guard handles this case exactly.
- **Use `canMatch` instead of `canActivate`**: `canMatch` is appropriate when evaluating whether a route should be matched at all, but `canActivate` is the standard JHipster pattern used by all other guarded routes and provides the same outcome here.

## Risks / Trade-offs

- **Stored URL for root path**: The guard stores `/` as the "previous URL" before redirecting to login. After login, `navigateToStoredUrl()` navigates back to `/`, which loads the home page. This is the intended behavior — authenticated users should see the home page.
- **Flashing content**: There may be a brief flash of the home component before the redirect, since the guard makes an HTTP call to `/api/account` before deciding. This is the same behavior as all other guarded routes and is mitigated by the existing auth interceptor flow which is fast for cached tokens.
