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
 * THIS SOFTWARE IS PROVREST_IDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCREST_IDENTAL, SPECIAL, EXEMPLARY, OR
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

/**
 * Rest Constants
 */
public final class Constants 
{
   
    public static final String API_VERSION = "version";
    public static final String HEADER_ORIGIN = "origin";
    
    
    public static final String PATH_BANNER_API = "bandeaugra/api";
    public static final String PATH_USER_INFORMATIONS = "/{" + Constants.API_VERSION + "}/userinformations";
    public static final String PATH_MYAPPS = "/{" + Constants.API_VERSION + "}/myapps";
    public static final String PATH_NOTIFICATIONS = "/{" + Constants.API_VERSION + "}/notifications";
    public static final String PATH_MY_FAVORITES = "/{" + Constants.API_VERSION + "}/myfavorites";
    
    
    
    public static final String PATH_IS_USER_AUTHENTICATED = "/{" + Constants.API_VERSION + "}/isuserauthenticated";
    
    public static final String ID = "id";
    
    public static final String EMPTY_OBJECT = "{}";
    public static final String ERROR_NOT_FOUND_VERSION = "Version not found";
    public static final String ERROR_NOT_FOUND_RESOURCE = "Resource not found";
    public static final String ERROR_BAD_REQUEST_EMPTY_PARAMETER = "Empty parameter";
    public static final String BANNERINFORMATIONS_PATH = "/bannerinformations";
    public static final String ERROR_USER_NOT_AUTHENTICATED = "USER_NOT_AUTHENTICATED";
    
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String METHODS_LIST = "GET, POST, DELETE, PUT";
    
}
