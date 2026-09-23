/*
* Copyright (c) 2002-2026, Mairie de Paris
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.portal.service.security.LuteceAuthentication;
import fr.paris.lutece.portal.service.security.LuteceUser;

/**
 * Tests the JSON the banner reads for the signed-in user.
 */
public class BannerInformationsRestTest
{
    /**
     * The JSON carries the given and family names of the user, escaped.
     */
    @Test
    public void testJsonBannerInformations( )
    {
        LuteceAuthentication authentication = (LuteceAuthentication) Proxy.newProxyInstance( getClass( ).getClassLoader( ),
                new Class<?> [ ] { LuteceAuthentication.class },
                ( proxy, method, args ) -> "getAuthServiceName".equals( method.getName( ) ) ? "test" : null );
        LuteceUser user = new LuteceUser( "e2e-citoyen", authentication )
        {
            private static final long serialVersionUID = 1L;
        };
        user.setUserInfo( LuteceUser.NAME_GIVEN, "Jean \"JJ\"" );
        user.setUserInfo( LuteceUser.NAME_FAMILY, "D'Arc" );

        String strJson = BannerInformationsRest.getJsonBannerInformations( user );

        assertTrue( strJson.contains( "\"firstName\":\"Jean \\\"JJ\\\"\"" ), strJson );
        assertTrue( strJson.contains( "\"lastName\":\"D'Arc\"" ), strJson );
        assertFalse( strJson.contains( "e2e-citoyen" ), strJson );
    }
}
