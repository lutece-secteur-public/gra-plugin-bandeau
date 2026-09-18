package fr.paris.lutece.plugins.bandeaugra.service;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

import fr.paris.lutece.plugins.bandeaugra.rs.Constants;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Calls the banner web services of the remote GRA site.
 */
@ApplicationScoped
public class RemoteSiteBandeauClientService
{
    private static final ObjectMapper MAPPER = new ObjectMapper( );

    @Inject
    @ConfigProperty( name = "beadeaugra.notificationsWsUrl" )
    private Optional<String> _strNotificationsUrl;

    @Inject
    @ConfigProperty( name = "beadeaugra.myappsWsUrl" )
    private Optional<String> _strMyappsUrl;

    @Inject
    @ConfigProperty( name = "beadeaugra.myfavoritesWsUrl" )
    private Optional<String> _strMyFavoritesUrl;

    @Inject
    @Named( "bandeaugra.requestAuthenticator" )
    private RequestAuthenticator _authenticator;

    public  String getMyApps( String strGuid )
    {
        return callBannerWS( _strMyappsUrl.orElse( "" ), strGuid );
    }
    
    public  String getNotifications( String strGuid )
    {
        String strResponse = callBannerWS( _strNotificationsUrl.orElse( "" ), strGuid );
        
        if(StringUtils.isEmpty(strResponse)) {
        	strResponse=createUnreadNotificationsJson();
        }
        return strResponse;
    }
    
    public  String getMyFavorites(String strGuid)
    {
       return callBannerWS( _strMyFavoritesUrl.orElse( "" ), strGuid );
    }
    
    
    private   String callBannerWS( String strWsUrl, String strGuid )
    {
        String strResponse = null;
        try
        {
            HttpAccess httpAccess = new HttpAccess(  );
            strResponse = httpAccess.doGet( strWsUrl+strGuid, _authenticator, null );
            
        }
        catch ( HttpAccessException e )
        {
            AppLogService.error( "Error connecting to '{}{}' : {}", strWsUrl, strGuid, e.getMessage( ), e );
           
        }

        return strResponse;
        
        
    }
    
    /**
     * Creates a JSON object containing the unread notifications count set to 0.
     * @return json
     */
    private String createUnreadNotificationsJson( )
    {
        ObjectNode json = MAPPER.createObjectNode( );
        json.put( Constants.TAG_NB_NOTIFICATIONS_UNREAD, 0 );

        return json.toString( );
    }
    

}
