/**
* Copyright (c) Microsoft Corporation
* 
* All rights reserved. 
* 
* MIT License
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
* (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, 
* publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
* subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
* ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH 
* THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.microsoft.azure.shortcuts.resources.implementation;

import com.microsoft.azure.management.network.models.SecurityRule;
import com.microsoft.azure.shortcuts.resources.NetworkSecurityGroup;
import com.microsoft.azure.shortcuts.resources.NetworkSecurityRule;
import com.microsoft.azure.shortcuts.resources.Protocol;

/***************************************************************
 * Implements logic for individual NSG
 ***************************************************************/
class NetworkSecurityRuleImpl 
	extends
		ChildResourceImpl<SecurityRule, NetworkSecurityGroupImpl>
	implements
		NetworkSecurityRule,
		NetworkSecurityRule.Definition<NetworkSecurityGroup.DefinitionCreatable> {

	NetworkSecurityRuleImpl(
			SecurityRule nativeItem,
			NetworkSecurityGroupImpl nsg) {
		super(nativeItem.getName(), nativeItem, nsg);
	}

	/***********************************************************
	 * Getters
	 ***********************************************************/

	/**************************************************************
	 * Setters (fluent interface)
	 **************************************************************/

	@Override
	public NetworkSecurityRuleImpl allowInbound() {
		return this
			.withDirection(Direction.INBOUND)
			.withPermission(Permission.ALLOW);
	}

	@Override
	public NetworkSecurityRuleImpl allowOutbound() {
		return this
			.withDirection(Direction.OUTBOUND)
			.withPermission(Permission.ALLOW);
	}

	@Override
	public NetworkSecurityRuleImpl denyInbound() {
		return this
			.withDirection(Direction.INBOUND)
			.withPermission(Permission.DENY);
	}

	@Override
	public NetworkSecurityRuleImpl denyOutbound() {
		return this
			.withDirection(Direction.OUTBOUND)
			.withPermission(Permission.DENY);
	}

	@Override
	public NetworkSecurityRuleImpl withInboundDirection() {
		return this.withDirection(Direction.INBOUND);
	}

	@Override
	public NetworkSecurityRuleImpl withOutboundDirection() {
		return this.withDirection(Direction.OUTBOUND);
	}

	@Override
	public NetworkSecurityRuleImpl withAllowPermission() {
		return this.withPermission(Permission.ALLOW);
	}

	@Override
	public NetworkSecurityRuleImpl withDenyPermission() {
		return this.withPermission(Permission.DENY);
	}

	@Override
	public NetworkSecurityRuleImpl withProtocol(Protocol protocol) {
		this.inner().setProtocol(protocol.toString());
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl withAnyProtocol() {
		return this.withProtocol(Protocol.ANY);
	}
		
	@Override
	public NetworkSecurityRuleImpl fromAddress(String cidr) {
		this.inner().setSourceAddressPrefix(cidr);
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl fromAnyAddress() {
		return this.fromAddress("*");
	}

	@Override
	public NetworkSecurityRuleImpl fromPort(int port) {
		this.inner().setSourcePortRange(String.valueOf(port));
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl fromAnyPort() {
		this.inner().setSourcePortRange("*");
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl fromPortRange(int from, int to) {
		this.inner().setSourcePortRange(String.valueOf(from) + "-" + String.valueOf(to));
		return this;
	}
		
	@Override
	public NetworkSecurityRuleImpl withPriority(int priority) {
		this.inner().setPriority(priority);
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl toAddress(String cidr) {
		this.inner().setDestinationAddressPrefix(cidr);
		return this;
	}
	
	@Override
	public NetworkSecurityRuleImpl toAnyAddress() {
		this.inner().setDestinationAddressPrefix("*");
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl toPort(int port) {
		this.inner().setDestinationPortRange(String.valueOf(port));
		return this;
	}
	
	@Override
	public NetworkSecurityRuleImpl toAnyPort() {
		this.inner().setDestinationPortRange("*");
		return this;
	}

	@Override
	public NetworkSecurityRuleImpl toPortRange(int from, int to) {
		this.inner().setDestinationPortRange(String.valueOf(from) + "-" + String.valueOf(to));
		return this;
	}
			
	/************************************************************
	 * Verbs
	 ************************************************************/

	@Override
	public NetworkSecurityGroupImpl attach() throws Exception {
		this.parent().inner().getSecurityRules().add(this.inner());
		return this.parent();
	}
			
	/*********************************************************
	 * Helpers
	 *********************************************************/
	private NetworkSecurityRuleImpl withDirection(Direction direction) {
		this.inner().setDirection(direction.toString());
		return this;
	}
		
	private NetworkSecurityRuleImpl withPermission(Permission permission) {
		this.inner().setAccess(permission.toString());
		return this;
	}
}
