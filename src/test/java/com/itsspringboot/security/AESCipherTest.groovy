package com.itsspringboot.security

import spock.lang.Specification


class AESCipherTest extends Specification {

    def aesKey

    def setup() {
        aesKey = "ggDINOo6G0BWv4dIFTL3Lx6tv71hC5vHJIqFNbrHtkw="
    }

    def "test encrypt method"() {
        given:
        def text = "test_text"
        def decodedText

        when:
        def encodedText = AESCipher.encrypt(text, aesKey)
        decodedText = AESCipher.decrypt(encodedText, aesKey)

        then:
        decodedText == text

    }
}