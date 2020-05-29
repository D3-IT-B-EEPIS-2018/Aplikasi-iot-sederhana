import network

class Wifi(object):
    ssid = "X.A.N.A 5.0"
    password = "bimbimbimbim"
    station = network.WLAN(network.STA_IF)
    state = ""

    def __init__(self, *args, **kwargs):
        print()
        print("2 / 3 -------------------------------------")
        print("Mengubungkan ke internet...")
        super(Wifi, self).__init__(*args, **kwargs)

    def connect(self):
        if self.station.isconnected() == True:
            self.state = "Internet : Telah terhubung"
            return
        self.station.active(True)
        self.station.disconnect()
        self.station.connect(self.ssid, self.password)
        while self.station.isconnected() == False:
            self.state = "Mencoba terhubung ke internet....."
            pass
        print("Internet : sukses terhubung")
        self.state = "terhubung"