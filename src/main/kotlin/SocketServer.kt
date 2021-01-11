import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

fun main(args: Array<String>) {
    val socketServer = SocketServer()
    socketServer.startServer()
}

class SocketServer {
    private lateinit var socket: DatagramSocket;

    private lateinit var socketThread: Thread

    private var running: Boolean = false

    fun startServer() {
        socketThread = Thread(Runnable {
            running = true
            socket = DatagramSocket(DATAGRAM_SOCKET_PORT)

            while (running) {
                val bytes = ByteArray(255)
                val inPacket = DatagramPacket(bytes, bytes.size)
                socket.receive(inPacket)
                val request = String(inPacket.data, 0, inPacket.length)
                println(request)
                if (request == DATAGRAM_SOCKET_REQUEST) {
                    val responseMsg: ByteArray = this.getIPAddress().encodeToByteArray();
                    socket.send(DatagramPacket(responseMsg, responseMsg.size, inPacket.socketAddress))
                }
            }
        })

        socketThread.start()
    }

    private fun closeServer() {
        socket!!.close();
        running = false
    }

    private fun getIPAddress(): String {
        val socket = DatagramSocket()
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        return socket.localAddress.hostAddress
    }
}
