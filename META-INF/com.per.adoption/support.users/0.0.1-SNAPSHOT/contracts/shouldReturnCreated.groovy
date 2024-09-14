package contracts

import org.apache.http.HttpHeaders
import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.MediaType


Contract.make {
    description 'Should return created status with the uuid in the Location header'
    request {
        method POST()
        url '/users'
        headers {
            header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        }
        body([
                "identityId": $(producer("115de516-ae8a-4db5-b63b-969c6696a848"), consumer(anyUuid())),
                "name"      : $(producer("Foundation"), consumer(anyNonBlankString())),
                "email"     : $(producer("user@email.com"), consumer(anyEmail())),
                "country"   : $(producer("Spain"), consumer(anyNonBlankString())),
                "role"      : $(producer("USER"), consumer(anyOf("USER", "FOUNDATION"))),
                "postalCode": $(producer("8798"), consumer(anyNumber()))
        ])
    }
    response {
        status CREATED()
        headers {
            header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            header(HttpHeaders.LOCATION, '/users/1')
        }
        body([
                id       : 1,
                createdAt: $(p(nonEmpty()), c("2024-09-13T13:04:38.724359400+02:00"))
        ])
    }
}