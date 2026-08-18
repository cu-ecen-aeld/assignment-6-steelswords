# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-steelswords.git;protocol=ssh;branch=main"
#SRC_URI = "git:///file:///home/tristan/classes/yocto/aesd-assignment-3.git;protocol=file;branch=main"

PV = "1.0+git${SRCPV}"
SRCREV = "6bef70ec2629b96083e9b8c67e9fbf15b1fc5735"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${S}/aesdsocket ${S}/aesdsocket-start-stop"
TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

EXTRA_OEMAKE += " 'CC=${CC}' 'CXX=${CXX}' 'LINKER=${CC}'"
do_compile () {
        oe_runmake clean
	#oe_runmake CROSS_COMPILE="${CROSS_COMPILE}"
        oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
        install -d "${D}/${bindir}"
        install -d "${D}/etc/rcS.d"
        install -m 755 "${S}/aesdsocket" "${D}/${bindir}"
        install -m 755 "${S}/aesdsocket-start-stop" "${D}/etc/rcS.d/S20-aesdsocket-start-stop"
}
