package fr.paris.lutece.plugins.bandeaugra.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import fr.paris.lutece.plugins.mylutece.modules.oauth2.service.Oauth2Service;
import fr.paris.lutece.util.signrequest.HeaderHashAuthenticator;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;
import fr.paris.lutece.util.signrequest.security.HashService;
import fr.paris.lutece.util.signrequest.security.Sha1HashService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Supplies the beans the plugin used to assemble in its Spring context: the OAuth2 data client reading the
 * banner user information, and the authenticator signing the calls to the remote banner web services.
 */
@ApplicationScoped
public class BandeaugraBeansProducer
{
    private static final String DEFAULT_DATA_SERVER_URI = "https://fcp.integ01.dev-franceconnect.fr/api/v1/userinfo";
    private static final String DEFAULT_TOKEN_METHOD = "HEADER";
    private static final String DEFAULT_SCOPES = "openid,profile,email,address,phone";
    private static final String DEFAULT_PRIVATE_KEY = "change me";
    private static final String CLIENT_NAME = "bannerInfoDataClient";

    @Inject
    private Oauth2Service _oauth2Service;

    /**
     * The data client the OAuth2 module calls back with the token, to read the user information the banner shows.
     *
     * @param dataServerUri
     *            address of the user information endpoint
     * @param tokenMethod
     *            how the token is carried, HEADER or QUERY
     * @param scopes
     *            the scopes to ask for, comma separated
     * @return the data client
     */
    @Produces
    @ApplicationScoped
    @Named( "bandeaugra.bannerInformationDataClient" )
    public BannerInformationDataClient produceBannerInformationDataClient(
            @ConfigProperty( name = "bandeaugra.dataclient.bannerInfo.dataServerUri" ) Optional<String> dataServerUri,
            @ConfigProperty( name = "bandeaugra.dataclient.bannerInfo.tokenMethod" ) Optional<String> tokenMethod,
            @ConfigProperty( name = "bandeaugra.dataclient.bannerInfo.scopes" ) Optional<String> scopes )
    {
        BannerInformationDataClient client = new BannerInformationDataClient( _oauth2Service );
        client.setName( CLIENT_NAME );
        client.setDataServerUri( dataServerUri.orElse( DEFAULT_DATA_SERVER_URI ) );
        client.setTokenMethod( tokenMethod.orElse( DEFAULT_TOKEN_METHOD ) );

        Set<String> setScopes = new HashSet<>( Arrays.asList( scopes.orElse( DEFAULT_SCOPES ).split( "," ) ) );
        client.setScope( setScopes );

        return client;
    }

    /**
     * The authenticator signing the calls to the remote banner web services.
     *
     * @param privateKey
     *            the shared secret the remote site checks the hash against
     * @return the authenticator
     */
    @Produces
    @ApplicationScoped
    @Named( "bandeaugra.requestAuthenticator" )
    public RequestAuthenticator produceRequestAuthenticator(
            @ConfigProperty( name = "bandeaugra.requestAuthenticator.privateKey" ) Optional<String> privateKey )
    {
        HashService hashService = new Sha1HashService( );
        HeaderHashAuthenticator authenticator = new HeaderHashAuthenticator( );
        authenticator.setHashService( hashService );
        authenticator.setPrivateKey( privateKey.orElse( DEFAULT_PRIVATE_KEY ) );

        return authenticator;
    }
}
