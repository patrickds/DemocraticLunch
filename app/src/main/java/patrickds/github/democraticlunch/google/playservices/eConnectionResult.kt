package patrickds.github.democraticlunch.google.playservices


enum class eConnectionResult {
    SUCCESS,
    SUSPENDED;

    fun succeeded() = this == SUCCESS
}