# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-steelswords.git;protocol=ssh;branch=main \
           file://0001-Only-build-scull-and-misc-modules.patch \
           file://0002-Add-install-target-to-makefiles.patch \
           file://0003-Tweak-Makefile.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "ff8532b950b40e7f6545414272fcdf64d641ad8f"

S = "${WORKDIR}/git"

inherit module

#MODULES_INSTALL_TARGET = "install"
#EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}/scull"
KERNELDIR = "${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_compile() {
    oe_runmake -C "${S}/scull"
    oe_runmake -C "${S}/misc-modules"
}

do_install() {
    oe_runmake -C "${S}/scull" modules_install INSTALL_MOD_PATH="${D}"
    oe_runmake -C "${S}/misc-modules" modules_install INSTALL_MOD_PATH="${D}"
}

#do_install() {
#    EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
#    run_oemake -C "${S}/scull" install
#    run_oemake -C "${S}/misc-modules" install
#}
RPROVIDES:${PN} += "kernel-module-scull"
