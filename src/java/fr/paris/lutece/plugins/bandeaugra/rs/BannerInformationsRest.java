/*
* Copyright (c) 2002-2018, Mairie de Paris
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*  1. Redistributions of source code must retain the above copyright notice
*     and the following disclaimer.
*
*  2. Redistributions in binary form must reproduce the above copyright notice
*     and the following disclaimer in the documentation and/or other materials
*     provided with the distribution.
*
*  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
*     contributors may be used to endorse or promote products derived from
*     this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*
* License 1.0
*/
package fr.paris.lutece.plugins.bandeaugra.rs;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.api.Responses;

import fr.paris.lutece.plugins.bandeaugra.business.BannerInformations;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.json.ErrorJsonResponse;
import fr.paris.lutece.util.json.JsonResponse;
import fr.paris.lutece.util.json.JsonUtil;

/**
 * BannerInformationsRest
 */
@Path( RestConstants.BASE_PATH + Constants.PATH_BANNER_API )
public class BannerInformationsRest
{
    private static final int VERSION_1 = 1;
    private Logger _logger = Logger.getLogger( RestConstants.REST_LOGGER );

    /**
     * Get User Informations
     * 
     * @param nVersion
     *            the Api version
     * @param request
     *            the http servlet request
     * @param response
     *            the http servlet response
     * @return
     */
    @GET
    @Path( Constants.PATH_USER_INFORMATIONS )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getUserInformations( @PathParam( Constants.API_VERSION ) Integer nVersion, @Context HttpServletRequest request,
            @Context HttpServletResponse response )
    {
        switch( nVersion )
        {
            case VERSION_1:
                return getUserInformationsV1( request, response );

            default:
                break;
        }

        throw new WebApplicationException( Responses.NOT_FOUND );
    }

  
    /**
     *  Get User Informations V1
     * @param request the request 
     * @param response the http servlet response
     * @return user informations
     */
    private Response getUserInformationsV1( HttpServletRequest request, HttpServletResponse response )
    {

        LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );

        String strHeaderOrigin = request.getHeader( Constants.HEADER_ORIGIN );
        String strAccessControlAllowOrigin = strHeaderOrigin;
        if ( user != null )
        {

            return Response.status( Response.Status.OK ).entity( BannerInformationsRest.getJsonBannerInformations( user ) )
                    .header( "Access-Control-Allow-Origin", strAccessControlAllowOrigin ).header( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" )
                    .header( "Access-Control-Allow-Credentials", "true" ).build( );
        }
        else
        {

            try
            {
                String strUrl = AppPathService.getBaseUrl( request ) + "/servlet/plugins/oauth2/callback?data_client=bannerInfoDataClient";
                URI urlRedirect = null;
                urlRedirect = new URI( strUrl );
                return Response.seeOther( urlRedirect ).header( "Access-Control-Allow-Origin", strAccessControlAllowOrigin ).header( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" )
                        .header( "Access-Control-Allow-Credentials", "true" ).build( );

            }
            catch( URISyntaxException e )
            {
                _logger.error( e );
            }

        }

        return Response.status( Response.Status.UNAUTHORIZED )
                .entity( JsonUtil.buildJsonResponse( new ErrorJsonResponse( Constants.ERROR_USER_NOT_AUTHENTICATED, Constants.ERROR_USER_NOT_AUTHENTICATED ) ) )
                .header( "Access-Control-Allow-Origin", strAccessControlAllowOrigin ).header( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" )
                .header( "Access-Control-Allow-Credentials", "true" ).build( );

    }
    
    
    
    
  /**
   * Get Json banner informatiobns
   * @param user the LuteceUser
   * @return Banner informations
   */
    public static String getJsonBannerInformations( LuteceUser user )
    {

        BannerInformations headbandInformations = new BannerInformations( );
        headbandInformations.setFirstName( user.getUserInfo( LuteceUser.NAME_GIVEN ) );
        headbandInformations.setLastName( user.getUserInfo( LuteceUser.NAME_FAMILY ) );
        return JsonUtil.buildJsonResponse( new JsonResponse( headbandInformations ) );
    }
    
    
    
    
    
    /**
     * Return JSON containing true if the user is authenticated
     * @param nVersion the api version
     * @param request the http request
     * @param response the http response
     * @return JSON containing true if the user is authenticated
     */
    @GET
    @Path( Constants.PATH_IS_USER_AUTHENTICATED )
    @Produces( MediaType.APPLICATION_JSON )
    public Response isUserAuthenticated( @PathParam( Constants.API_VERSION ) Integer nVersion, @Context HttpServletRequest request,
            @Context HttpServletResponse response )
    {
        switch( nVersion )
        {
            case VERSION_1:
                return isUserAuthenticatedV1( request, response );

            default:
                break;
        }

        throw new WebApplicationException( Responses.NOT_FOUND );
    }

    
    /**
     * Return JSON containing true if the user is authenticated
     * @param request the http request
     * @param response the http response
     * @return JSON containing true if the user is authenticated
     */
    private Response isUserAuthenticatedV1( HttpServletRequest request, HttpServletResponse response )
    {

        LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );

        String strHeaderOrigin = request.getHeader( Constants.HEADER_ORIGIN );
        String strAccessControlAllowOrigin = strHeaderOrigin;
        return Response.status( Response.Status.OK ).entity( JsonUtil.buildJsonResponse( new JsonResponse( user != null )) )
                    .header( "Access-Control-Allow-Origin", strAccessControlAllowOrigin ).header( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" )
                    .header( "Access-Control-Allow-Credentials", "true" ).build( );
       
    }
    

}
