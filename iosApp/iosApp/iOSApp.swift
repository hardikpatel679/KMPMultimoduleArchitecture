import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        // MANUAL ENVIRONMENT SETTING
        // Change this to .dev, .mock, or .prod as needed
        BuildContext.shared.environment = .mock
        
        print("iOS App started with environment: \(BuildContext.shared.environment)")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
