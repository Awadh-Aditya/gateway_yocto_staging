SUMMARY = "Deployment of example files to run hostapd on Toradex demo images"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN} = "hostapd"

S = "${WORKDIR}"

SRC_URI = " \
    file://enable-wifi.service \
    file://hostapd-example.service \
    file://hostapd-example.network \
    file://hostapd-tdx-demo-img.conf \
"

inherit allarch systemd

SYSTEMD_SERVICE_${PN} = "hostapd-example.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

do_install() {
    install -d ${D}${systemd_unitdir}/system/ ${D}${systemd_unitdir}/network/ ${D}${sysconfdir}/
    install -m 0644 enable-wifi.service ${D}${systemd_unitdir}/system/
    install -m 0644 hostapd-example.network ${D}${systemd_unitdir}/network/
    install -m 0644 hostapd-example.service ${D}${systemd_unitdir}/system/
    install -m 0644 hostapd-tdx-demo-img.conf ${D}${sysconfdir}/
    sed -i -e 's,@SBINDIR@,${sbindir},g' -e 's,@SYSCONFDIR@,${sysconfdir},g' ${D}${systemd_unitdir}/system/hostapd-example.service
}

FILES_${PN} += " \
    ${systemd_unitdir}/system/* \
    ${systemd_unitdir}/network/hostapd-example.network \
    ${sysconfdir}/hostapd-tdx-demo-img.conf \
"

