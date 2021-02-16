high-availability {
    vrrp {
        group langroup1 {
            interface eth1
            virtual-address 10.0.5.1/24
            vrid 51
        }
        group optgroup1 {
            interface eth2
            virtual-address 10.0.6.1/24
            vrid 61
        }
        group wangroup1 {
            interface eth0
            virtual-address 10.0.17.71/24
            vrid 151
        }
    }
}
interfaces {
    ethernet eth0 {
        address 10.0.17.51/24
        description WAN
        hw-id 00:50:56:b3:fc:1b
    }
    ethernet eth1 {
        address 10.0.5.3/24
        description LAN
        hw-id 00:50:56:b3:07:9e
    }
    ethernet eth2 {
        address 10.0.6.3/24
        description OPT
        hw-id 00:50:56:b3:73:05
    }
    loopback lo {
    }
}
nat {
    destination {
        rule 10 {
            description "access web01 from WAN"
            destination {
                address 10.0.17.71
                port 80
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 10.0.6.5
            }
        }
    }
    source {
        rule 10 {
            outbound-interface eth0
            source {
                address 10.0.5.0/24
            }
            translation {
                address masquerade
            }
        }
        rule 20 {
            outbound-interface eth0
            source {
                address 10.0.6.0/24
            }
            translation {
                address masquerade
            }
        }
    }
}
protocols {
    static {
        route 0.0.0.0/0 {
            next-hop 10.0.17.2 {
            }
        }
    }
}
service {
    dns {
        forwarding {
            allow-from 10.0.5.0/24
            allow-from 10.0.6.0/24
            listen-address 10.0.5.1
        }
    }
    ssh {
        listen-address 0.0.0.0
    }
}
system {
    config-management {
        commit-revisions 100
    }
    host-name vyos02
    login {
        user alex {
            authentication {
                encrypted-password $6$8nrll7PtGgmUwb.i$aCkng/NaCevmQzGHlx827ijCfolG/jQleKAh8GhdghYpiNT.VHh/ljH44/jAA3H3kDaBFDKgsyqQmgWb/LaS10
                public-keys id_lan {
                    key AAAAB3NzaC1yc2EAAAADAQABAAABgQCg1b/htShVK8JYIlJiBK2lKmDGYrASBS2ItEFdcOA5OWfLoObKM2U3rYa7k3XwGEzf6y9omSGu2+BlQoC30GTKzrb03vu3ClOD2fJ5mtqDMJqm9mFrqHpGZuQlg6X26iggNuA1dcZdzwh75ySfq8u+hUWmYfTncyKDQ8pl8sEFRiRrXM0e+xjxP4mdDXHCBXev0RkJxPYQ1uxH/CTdbq0ef1TjDB9c51zaX5Mu23WJIlwPqPjTipInUD1mU5O8+DN6gsQ4kpnHprfmatILoRlvSKjf5D2Eha/yMgLfh9ys1OrbAZTvvrd/9Sq+f+bp9/rqGYt8ex+Rd6zRKiTzvvEfRIqtbF+Du1mriTcwbhiyugJoQMePHpGyAtexAL39TeFnMHbEtGmQvRNt+CzaZnwGOeUfYW1UzOVqbs1rzVEXiu7GUGC6YQy/bhAFd1YpMZDvVRrN0NocCfGZ9Yai1RsyylQs1kAa/0mi98lxJIik8DyvZcLmR9fT/WrqOhvPoRs=
                    type ssh-rsa
                }
            }
        }
        user vyos {
            authentication {
                encrypted-password $6$lEgayS1Fn7$Y4.VS/yBAdhxkmgDAzrzUN/ibgmO9rRgblmnMMxvFqN1D89m/XTqRxk7.DW60cj5Dvv9PC5c0n0V3M7weIk9v/
                plaintext-password ""
            }
        }
    }
    name-server 10.0.17.2
    ntp {
        server 0.pool.ntp.org {
        }
        server 1.pool.ntp.org {
        }
        server 2.pool.ntp.org {
        }
    }
    syslog {
        global {
            facility all {
                level info
            }
            facility protocols {
                level debug
            }
        }
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "broadcast-relay@1:cluster@1:config-management@1:conntrack@1:conntrack-sync@1:dhcp-relay@2:dhcp-server@5:dhcpv6-server@1:dns-forwarding@3:firewall@5:https@2:interfaces@18:ipoe-server@1:ipsec@5:l2tp@3:lldp@1:mdns@1:nat@5:ntp@1:pppoe-server@5:pptp@2:qos@1:quagga@6:salt@1:snmp@2:ssh@2:sstp@3:system@20:vrrp@2:vyos-accel-ppp@2:wanloadbalance@3:webgui@1:webproxy@2:zone-policy@1"
// Release version: 1.3-rolling-202012291104
