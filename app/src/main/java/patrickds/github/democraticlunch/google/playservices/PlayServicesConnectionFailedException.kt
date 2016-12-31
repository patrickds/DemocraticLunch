package patrickds.github.democraticlunch.google.playservices

class PlayServicesConnectionFailedException(errorMessage: String?, errorCode: Int)
    : Exception(
        "Play services connection failed" +
        "\nError message: " + (errorMessage ?: "") +
        "\nError code: $errorCode")
