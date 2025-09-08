

# Requirements

* Java 21 (latest LTS version as of submission)
* Maven 3.9+
* JUnit 5
* Selenium 4
* Google Chrome

# UI Automation Exercise (Java + Selenium + JUnit 5)

**File:** `src/test/java/codingassignment/tests/ui/PurchaseFlowTests.java`
**What it does:** 

* Opens home
* Accepts cookies
* Searches for “sofa”, opens the first product, adds to bag.
* Searches for “table”, opens the third product, adds to bag.
* Navigates to the shopping bag and asserts there are 2 items.
* Opens “Apply discount code”, enters a random 15-char code, clicks Apply, and asserts the error message for an invalid code appears.
* Failure artifacts are saved to `target/screenshots/`.

**Run (IDE)**

* Open `codingassignment.tests.ui.PurchaseFlowTests` and click run.

**Run (Maven)**

`mvn -q -Dtest=codingassignment.tests.ui.PurchaseFlowTests test`

**Options**

* Headless: add JVM option `-Dheadless=true` to the above run command.
* Base URL (defaults to `https://ikea.com/us/en`): `-DbaseUrl=https://<your-url>`

---

## Project layout

```
src
  /test/java/codingassignment
    /uiElements
      /core          # BaseUi (waits/util), BasePage, BaseComponent
      /components    # Header, SearchBar, ProductCard, CartItem, RecommendationsModal
      /pages         # HomePageUs, ProductsCatalogPage, ProductPage, ShoppingBagPage
    /tests
      /base          # BaseUiTest (WebDriver setup, screenshots on failure)
      /ui            # PurchaseFlowTests (end-to-end UI test)
      /api           # API tests (Rest-Assured)
      /util          # Rand (util for random alphanumeric string), NameFormatter, NameCli (demo for NameFormatter)
```

---

## Design highlights

* **Page Objects + Components**
  Page object models both for full pages and smaller, reusable components (e.g., `Header`, `SearchBar`, `ProductCard`).

* **Scoped components**
  `BaseComponent` wires `@FindBy` **within a root** using `DefaultElementLocatorFactory(root)`. This keeps locators from leaking outside themselves.

* **Centralized waits & helpers**
  `BaseUi` owns `WebDriverWait` and exposes helpers like `clickWhenClickable`, `typeWhenVisible`, `waitUntilVisible`, plus a simple cookie-consent handler as it otherwise blocks some page elements. Timeouts all live here too.

* **Lazy header**
  `BasePage.header()` is a lazy getter so navigation doesn’t leave you with stale header elements.

* **Selector strategy**
  * Prefer IDs and aria-label attributes when present (stable & accessible).
  * Tolerate CSS-module hashes via `*[class*='...']`.
  * Use scoped XPath **only** when CSS cannot express an ancestor relation (e.g., coupon apply button).

* **Flake handling**
  * Actions wait for visibility/clickability.

This assignment frames a new automation project without clarifying product stability. I built for:

* **Readability** (tests read like test cases),
* **Modularity** (single sources of truth),
* **Leanness** (avoid unused selectors that may drift),
* **Self-documenting code** (minimal comments, explain *why* only when the intent behind a choice isn’t obvious).

If this were a shared, stable app, I’d expand page objects and add simple smoke checks on unused locators.

---

# API exercise (Rest-Assured)

**File:** `src/test/java/codingassignment/tests/api/RestApiTest.java`
**What it does:** 

* Targets `https://api.restful-api.dev/objects`.
* Asserts 1 - IDs are unique and 2 - every `name` contains “Apple”.
* The second assertion intentionally fails with the live dataset and prints which names violate the rule.
* Uses `rest-assured` and JUnit Assertions.
* `LinkedHashSet` lets us detect duplicates in a single pass while preserving order. 
* `distinct()` avoids repeat reporting.

**Run (IDE):**

* Open codingassignment.tests.api.RestApiTest and click run.

**Run (Maven):**

`mvn -q -Dtest=codingassignment.tests.api.RestApiTest test`

---

# Refactor exercise – `NameFormatter`

**File:** `src/test/java/codingassignment/tests/util/NameFormatter.java`
**What it does:** 

* Converts a name string (0..N words) into initials like:
* `"Bruno Mars"` -> `"B.M."`
* `"Dave M Jones"` -> `"D.M.J."`
* `"MichaelSmith"` -> `"M."`

**Improvements over the original**

* Trims input
* Handles `null` safely.
* Uses regex to split on **any whitespace** which copes with multiple spaces.
* Supports any number of middle names.
* Reduces risk of out of bounds exceptions

**Run (IDE)**

* A CLI file (`NameCli`) lets you try out the method from the IDE via Program Arguments.

---

## Troubleshooting

* **CDP/DevTools warning on Chrome 140 or later**
* Expected. Selenium runs fine without DevTools. If you prefer a warning-free run, pin Chrome-for-Testing 139 locally and pass a custom binary path via `-Dchrome.binary=<path>`.

* **Screenshots not appearing**
* Ensure tests run **from Maven/IDE**, not `jshell`; look under `target/screenshots/`. The directory is created in `BaseUiTest`.
  
## Security Note

* Rest Assured 5.5.6, which is the newest version, has a vulnerability in one of its dependencies, but the test using it hits a public demo API. No secrets or user PII are involved.