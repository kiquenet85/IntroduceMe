package com.kiquenet.introduceme.ui.settings

/**
 * @author n.diazgranados
 */
class Settings {

    private var log: Log = Log()

    /**
     * Current environment name.
     */
    var baseUrl: String? = null

    /**
     * Get default service timeout.
     */
    var serviceTimeOut: Int = 60
    /**
     * Get default connection timeout.
     */
    var connectionTimeOut: Int = 30

    /**
     * Sets current Logging Level.
     */
    var loggingLevel: Int
        get() = log.level
        set(newLevel) {
            log.level = newLevel
        }

    private inner class Log {
        internal var level: Int = android.util.Log.INFO
    }
}
