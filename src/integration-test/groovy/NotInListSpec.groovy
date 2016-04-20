import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails3.notinlist.Foo
import grails3.notinlist.FooStatus
import spock.lang.Specification

/**
 * Created by mikeh on 4/20/16.
 */

@Integration
@Rollback
class NotInListSpec extends Specification{

    def "by status"() {

        given:

        def goodFoo = new Foo(name:"good",status:FooStatus.GOOD).save(failOnError:true, flush:true)

        when:

        def foos =Foo.findAllByStatus(FooStatus.GOOD)

        then:

        foos.size()==1

        foos[0]==goodFoo

    }

    def "in list"() {

        given:

        def uglyFoo = new Foo(name:"ugly",status:FooStatus.UGLY).save(failOnError:true, flush:true)

        when:

        def foos = Foo.findAllByStatusInList([FooStatus.BAD,FooStatus.UGLY])

        then:

        foos.size()==1

        foos[0]==uglyFoo
    }


    def "not in list"() {
        given:

        def goodFoo = new Foo(name:"good",status:FooStatus.GOOD).save(failOnError:true, flush:true)

        when:

        def foos = Foo.findAllByStatusNotInList([FooStatus.BAD,FooStatus.UGLY])


        then:

        foos.size()==1

        foos[0]==goodFoo

    }
}
