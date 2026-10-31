# SAYDUR AI — Starter Android project

এটি প্রথম ধাপের একটি Android starter project। এতে আছে:
- SAYDUR AI-এর প্রাথমিক Home Screen
- Android Call Screening role চাওয়ার বাটন
- ইনকামিং SIM কল শনাক্ত হলে নোটিফিকেশন
- কল স্বাভাবিকভাবে রিং করবে; অ্যাপ কল ধরবে/রেকর্ড করবে/অডিও শুনবে না

## গুরুত্বপূর্ণ সীমাবদ্ধতা
- এই ভার্সন AI voice receptionist নয়।
- এটি SIM কল অটো-রিসিভ বা ট্রান্সফার করে না।
- সাধারণ WhatsApp কল এই Android CallScreeningService দিয়ে ধরা যায় না।
- Cloud AI, OpenAI API, Firebase ও WhatsApp Business Calling এখনও যুক্ত করা হয়নি।
- Call screening role চালু করলে Android-এর কল-স্ক্রিনিং দায়িত্ব এই অ্যাপ পেতে পারে। পরে Settings থেকে role ফিরিয়ে নিতে পারবেন।
- ফোন/OS/OEM অনুযায়ী role বা caller number দেখানোর আচরণ ভিন্ন হতে পারে।

## মোবাইল থেকে APK বানানোর সহজ পথ (GitHub Actions)
1. GitHub-এ সাইন ইন করুন এবং নতুন **Public repository** তৈরি করুন, নাম `saydur-ai`।
2. এই ZIP-এর ভেতরের সব ফাইল ও ফোল্ডার repository-তে আপলোড করুন (ZIP ফাইলটি নিজে নয়; extract করে ভিতরের ফাইলগুলো)।
3. `Actions` ট্যাব খুলে workflow চালু/অনুমোদন করতে বললে Allow করুন।
4. `Build SAYDUR AI APK` workflow খুলে **Run workflow** চাপুন।
5. কাজ শেষ হলে নিচের Artifacts থেকে `SAYDUR-AI-debug` ডাউনলোড করুন।
6. ZIP extract করে `app-debug.apk` ইনস্টল করুন। Android চাইলে শুধু এই ইনস্টলারের জন্য “Install unknown apps” অনুমতি দিন।

## Build configuration
- Android Gradle Plugin 8.7.3
- Gradle 8.9
- Java 17
- compileSdk 35, minSdk 29

এই starter build-এর জন্য কোনো API key লাগবে না। GitHub Actions-এর ফ্রি সীমা ও শর্ত প্রযোজ্য।
