import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

fun main(args: Array<String>) {
    val socketClient = SocketClient()
    socketClient.startClient()
}

class SocketClient {
    private lateinit var socket: DatagramSocket

    private lateinit var outPacket: DatagramPacket

    private lateinit var inPacket: DatagramPacket

    fun startClient() {
        socket = DatagramSocket()
        val bytes = DATAGRAM_SOCKET_REQUEST.encodeToByteArray()
        outPacket =
            DatagramPacket(bytes, bytes.size, InetAddress.getByName(DATAGRAM_BROADCAST_ADDR), DATAGRAM_SOCKET_PORT)
        socket.send(outPacket)
        val inBytes = ByteArray(255)
        inPacket = DatagramPacket(inBytes, inBytes.size)
        socket.receive(inPacket)
        val resp = String(inPacket.data, 0, inPacket.length)
        println(resp)
    }
}
