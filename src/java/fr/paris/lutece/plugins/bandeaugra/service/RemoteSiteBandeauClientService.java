package fr.paris.lutece.plugins.bandeaugra.service;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import fr.paris.lutece.plugins.bandeaugra.rs.Constants;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import fr.paris.lutece.util.signrequest.HeaderHashAuthenticator;

public class RemoteSiteBandeauClientService
{
    private static  RemoteSiteBandeauClientService _singleton;
    private static final String  PROPERTY_BANDEAU_MY_APPS_URL = "beadeaugra.myappsWsUrl";
    private static final String  PROPERTY_BANDEAU_MY_FAVORITES = "beadeaugra.myfavoritesWsUrl";
    
    private static final String  PROPERTY_BANDEAU_NOTIFICATIONS_URL = "beadeaugra.notificationsWsUrl";
    private static final String  BEAN_AUTHENTICATOR = "bandeaugra.requestAuthenticator";
    private  String _strNotificationsUrl;
    private  String _strMyappsUrl;
    private  String _strMyFavoritesUrl;
    private HeaderHashAuthenticator _authenticator = null;
    
    public static RemoteSiteBandeauClientService getInstance( )
    {
        if( _singleton == null )
       {
           
           _singleton=new RemoteSiteBandeauClientService();
           _singleton. _strNotificationsUrl=AppPropertiesService.getProperty( PROPERTY_BANDEAU_NOTIFICATIONS_URL );
           _singleton. _strMyappsUrl=AppPropertiesService.getProperty( PROPERTY_BANDEAU_MY_APPS_URL );
           _singleton. _strMyFavoritesUrl=AppPropertiesService.getProperty( PROPERTY_BANDEAU_MY_FAVORITES );
           _singleton._authenticator= SpringContextService.getBean( BEAN_AUTHENTICATOR );       
       }
        return _singleton;
    }
    
    public  String getMyApps( String strGuid )
    {
        return callBannerWS( _strMyappsUrl,strGuid );
    }
    
    public  String getNotifications( String strGuid )
    {
        String strResponse = callBannerWS( _strNotificationsUrl, strGuid );
        
        if(StringUtils.isEmpty(strResponse)) {
        	strResponse=createUnreadNotificationsJson();
        }
        return strResponse;
    }
    
    public  String getMyFavorites(String strGuid)
    {
       return callBannerWS( _strMyFavoritesUrl,strGuid );
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
            String strError = "Error connecting to '" + strWsUrl+strGuid + "' : ";
            AppLogService.error( strError + e.getMessage(  ), e );
           
        }

        return strResponse;
        
        
    }
    
    /**
     * Creates a JSON object containing the unread notifications count set to 0.
     * @return json
     */
    @SuppressWarnings("unchecked")
	private String createUnreadNotificationsJson() {
        JSONObject json = new JSONObject( );
        json.put(Constants.TAG_NB_NOTIFICATIONS_UNREAD,0);

        return json.toJSONString();
    }
    

}
