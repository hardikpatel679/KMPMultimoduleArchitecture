import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KmpDIKt.doInitKoin()

        // Renamed to AppBuildContext to avoid potential name conflicts
        // and using full names for clarity
        let sharedContext = AppBuildContext.shared
        sharedContext.environment = AppEnvironment.dev
        
        print("iOS App started with environment: \(sharedContext.environment)")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
