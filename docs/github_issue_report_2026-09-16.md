# Daily GitHub Issue Report - 2026-09-16

## Summary
- **Total Open Issues:** 9
- **Issues Updated Recently (Last 14 days):** 6 (#144, #143, #133 follow-ups, #118, #104, #103)
- **Top Priority Fixes:**
  1. **Issue #144**: Block silent install when VirusTotal or trackers detect security flags.
  2. **Issue #133 (Follow-up UX)**: Add scrollbar for long changelogs and improve APK asset name readability in Update Tracker.
  3. **Issue #118**: Enhance Backup & Restore to support OBB / Media data for apps that store databases in Android/obb.

---

## Detailed Analysis of All Open Issues

### 1. Issue #144: Silent install is not blocked for approved apps when there are flags by virustotal
- **Author:** @Raudrobot (Opened ~4 days ago)
- **Status:** OPEN (0 comments)
- **Problem Statement:**
  Currently, when auto-approval / silent installation (Shizuku, Root, or Dhizuku) is active, packages from approved sources bypass manual review. Even if VirusTotal scanning flags malicious signatures or trackers, the install proceeds automatically in the background.
- **Impact:** High security risk for users who rely on VirusTotal as an automated safeguard.
- **Recommended Solution:**
  In the installation coordinator / pre-install validation logic:
  - Check VirusTotal scan results before invoking silent installation.
  - If `positives > 0` or severe trackers are flagged:
    - Automatically intercept and halt silent installation.
    - Fallback to opening the interactive Install Dialog / prompt the user with a security warning and manual review confirmation.

---

### 2. Issue #143: On Phone Android how to extract the apkm to apk?
- **Author:** @Hot12345 (Opened ~4 days ago)
- **Status:** OPEN (1 comment from community member @JohnSmit268)
- **Summary:**
  User asked how to extract an `.apkm` file into a single `.apk` for their Android TV / media box which only supports standalone `.apk`.
- **Analysis:**
  This is a user support / Q&A inquiry rather than a bug. As community member @JohnSmit268 correctly noted, Universal Installer already supports installing `.apks` directly on Android TV, or standalone tools like AntiSplit can merge them.
- **Recommended Action:**
  Provide an official polite response pointing to our Android TV build (`:tv`) and close with label `question`.

---

### 3. Issue #133 (New Comments): Track app issues — Asset Name Readability & Changelog Scroll
- **Author:** @Jaanyaar (Updated ~4 days ago)
- **Status:** CLOSED (with 2 actionable follow-up UX suggestions)
- **New Feedback:**
  1. **APK Asset Name Truncation:** In the release asset selection dialog, long asset filenames are truncated/difficult to distinguish between different ABI variants (e.g. `arm64-v8a` vs `v7a`).
  2. **Changelog Scrollbar:** Long release notes and changelogs lack a dedicated scrollbar, making it difficult to read lengthy release logs.
- **Recommended Solution:**
  - Enhance `ReleaseNotesDialog` / `ChangelogDialog` with a scrollbar and expanded text view.
  - In `UpdateAssetPicker`, display distinct architecture chips or highlight differences (ABI, flavor) rather than truncating long filenames.

---

### 4. Issue #118: Backup enhancement
- **Author:** @Jaanyaar (Reopened ~10 days ago)
- **Status:** OPEN (Assigned to @nqmgaming)
- **Problem Statement:**
  Universal Installer recently added full backup/restore for app settings, profiles, and metadata. However, the user pointed out that certain apps (e.g. dictionary apps like LDOCE) store large offline databases in `/storage/emulated/0/Android/obb/<package>` or `/Android/media/<package>`.
- **Feasibility & Recommendation:**
  Add an optional checkbox in the Backup Dialog: *"Include OBB and Media data"* (for Root / Shizuku modes with `All Files Access`), archiving `/Android/obb/<package>` alongside APK and data.

---

### 5. Issue #104: [Bug] Compatibility notification and theme deformation on Xiaomi devices
- **Author:** @nqmgaming (Opened ~1 month ago)
- **Status:** OPEN (Assigned to @nqmgaming)
- **Summary:**
  Addressed in commit `d92ba4d` with Xiaomi/MIUI optimization guidance in error dialogs and onboarding. Theme deformation was verified to be an OS-level MIUI side effect when MIUI optimization is toggled off.
- **Current State:**
  Code fix is already implemented in `main`. Awaiting external confirmation on physical MIUI/HyperOS devices before closing.

---

### 6. Issue #103: Install applications from external storage
- **Author:** @user-15031 (Opened ~1 month ago)
- **Status:** OPEN (Assigned to @nqmgaming)
- **Summary:**
  User asked to install applications directly onto a physical SD card / external storage to save internal space.
- **Analysis:**
  Modern Android (Android 10+) strictly enforces Scoped Storage and disallows direct APK/OBB installation onto external SD cards (FAT32/exFAT lack POSIX permissions and SELinux contexts). We asked the reporter for reference open-source apps that accomplish this without kernel bind mounts. No reply received.
- **Recommended Action:**
  Keep on hold or close as `wontfix` / `not-feasible` unless the user provides a workable system mechanism.

---

### 7. Issue #95: More Options
- **Author:** @dmarquezbaeza-ux (Opened ~2 months ago)
- **Status:** OPEN
- **Summary:**
  Feature request asking for advanced installation flags (similar to "Install with Options"), such as `--bypass-low-target-sdk-block`, `INSTALL_ALLOW_TEST`, `INSTALL_GRANT_RUNTIME_PERMISSIONS`, etc.
- **Priority:** Low / Future feature candidate for power users.

---

### 8. Issue #92: Bug: parsing error installing app
- **Author:** @BuddhaDiedLaughing (Opened ~2.5 months ago)
- **Status:** OPEN (Awaiting user info)
- **Summary:**
  Reporter had a parsing error installing ReAppzuku 1.8.4. Maintainer tested the APK on Android 13; it parsed and signed completely normally. Requested Android version, install mode, and diagnostic logs on Aug 2, 2026.
- **Recommended Action:**
  No response from reporter for over 6 weeks. Can be closed with `stale` / `cannot-reproduce`.

---

### 9. Issue #59: More native virustotal check for installed apps
- **Author:** @Teophrast (Opened June 2026)
- **Status:** OPEN
- **Summary:**
  Proposes batch scanning of all installed apps in ManageScreen with color indicators (green shield = safe, red = infected, yellow = exceeded quota). Also suggests tracker detection.
- **Priority:** Low / Future enhancement.

---

### 10. Issue #13: Keep Android Open
- **Author:** @Bryancyriel7 (Opened April 2026)
- **Status:** OPEN
- **Summary:**
  Discussion / manifesto issue regarding Google's restrictions on sideloading and developer registration.
- **Recommended Action:**
  Close as `not-planned` / `discussion` as there is no code action to take.

---

## Action Plan & Recommendations

| Priority | Issue | Action Description | Suggested Owner |
| :--- | :--- | :--- | :--- |
| **P1** | **#144** | Implement auto-fallback: block silent install and show manual review prompt when VirusTotal flags > 0 | Agent / Core |
| **P2** | **#133** | Add scrollbar for long changelogs and improve APK asset name display in Update Tracker | UI / App |
| **P3** | **#118** | Plan OBB & Media directory inclusion in Backup & Restore | Data / Backup |
| **P4** | **#143** | Reply with explanation and close as answered `question` | Community / Maintainer |
| **P4** | **#92** | Close as `cannot reproduce` / `stale` (no response in 45 days) | Maintainer |
| **P4** | **#13** | Close as `discussion` / `not planned` | Maintainer |
