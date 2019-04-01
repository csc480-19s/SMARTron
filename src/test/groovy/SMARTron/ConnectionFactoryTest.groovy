package SMARTron.Database

import spock.lang.*

import java.sql.SQLException

class ConnectionFactoryTest extends Specification{

    ConnectionFactory cf = new ConnectionFactory()

    def "standard getConnection"() {
        when:
        cf.getConnection()

        then:
        notThrown SQLException
    }

    def "incorrect password"() {
        given:
        def password = "wrong"
        def cf = new ConnectionFactory(password: password)

        when:
        cf.getConnection()

        then:
        thrown SQLException
    }

    def "incorrect username"() {
        given:
        def user = "wrong"
        def cf = new ConnectionFactory(user: user)

        when:
        cf.getConnection()

        then:
        thrown SQLException
    }

    def "wrong data types"() {
        given:
        def user = 1
        def password = 1
        def cf = new ConnectionFactory(user: user, password: password)

        when:
        cf.getConnection()

        then:
        thrown SQLException
    }


}