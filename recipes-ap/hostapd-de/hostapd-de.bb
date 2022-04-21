SUMMARY = "Hostapd hotspot for digital entomologist"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN} = "hostapd"

S = "${WORKDIR}"

SRC_URI = " \
    file://enable-wifi.service \
    file://hostapd-de.service \
    file://hostapd-de.network \
    file://hostapd-de-ap.conf \
    file://wifi.sh \
    "

inherit allarch systemd

SYSTEMD_SERVICE_${PN} = "hostapd-de.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

do_install() {
    install -d ${D}${systemd_unitdir}/system/ ${D}${systemd_unitdir}/network/ ${D}${sysconfdir}/ ${D}${sbindir}/hostapd-DE/
    install -m 0644 enable-wifi.service ${D}${systemd_unitdir}/system/
    install -m 0644 hostapd-de.network ${D}${systemd_unitdir}/network/
    install -m 0644 hostapd-de.service ${D}${systemd_unitdir}/system/
    install -m 0644 hostapd-de-ap.conf ${D}${sysconfdir}/
    install -m 0644 wifi.sh ${D}${sbindir}/hostapd-DE/
    sed -i -e 's,@SBINDIR@,${sbindir},g' -e 's,@SYSCONFDIR@,${sysconfdir},g' ${D}${systemd_unitdir}/system/hostapd-de.service
}

FILES_${PN} += " \
    ${systemd_unitdir}/system/* \
    ${systemd_unitdir}/network/hostapd-de.network \
    ${sysconfdir}/hostapd-de-ap.conf \
"

